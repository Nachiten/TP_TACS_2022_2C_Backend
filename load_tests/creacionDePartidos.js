import http from 'k6/http';
import { check, group, sleep, fail } from 'k6';
import { SharedArray } from 'k6/data';

export const options = {
  stages: [{ target: 30, duration: '1m' }],
  thresholds: {
    'http_req_duration': ['p(95)<500', 'p(99)<1500'],
    'http_req_duration': ['avg<400'],
    'http_req_duration': ['avg<600', 'max<1000'],
  },
};

const data = new SharedArray('Address', function () {
  // All heavy work (opening and processing big files for example) should be done inside here.
  // This way it will happen only once and the result will be shared between all VUs, saving time and memory.
  const f = JSON.parse(open('./clubes.json'))
  return f; // f must be an array
});

function randomDate(start, end) {
  return new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
}

function randomAddress(){
    return data[Math.floor(Math.random() * data.length)].direccion;
}

const BASE_URL = 'http://tacsutn.ddns.net/api';

const headers =  {headers: { 'Content-Type': 'application/json' }}



export default () => {
  var body =JSON.stringify({"startingDateTime":randomDate(new Date(2030, 0, 1), new Date()),"location":randomAddress()})

  const res = http.post(`${BASE_URL}/matches`, body, headers);

  check(res, { 'Matches created correctly': (r) => r.status === 200 })
   
  sleep(1);
};