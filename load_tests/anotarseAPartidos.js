import http from 'k6/http';
import { check, group, sleep, fail } from 'k6';
import { SharedArray } from 'k6/data';

export const options = {
  iterations:100,
  duration: '1m',
  //stages: [{ target: 100, duration: '1m' }],
  vus: 100,
  thresholds: {
    'http_req_duration': ['p(95)<500', 'p(99)<1500'],
    'http_req_duration': ['avg<400'],
    'http_req_duration': ['avg<600', 'max<1000'],
  },
};



const BASE_URL = 'http://tacsutn.ddns.net/api';

const headers =  {headers: { 'Content-Type': 'application/json' }}

const names = new SharedArray('Names', function () {
        // All heavy work (opening and processing big files for example) should be done inside here.
        // This way it will happen only once and the result will be shared between all VUs, saving time and memory.
        const f = JSON.parse(open('./names.json'))
        return f; // f must be an array
});

const matchesID = new SharedArray('Matches', function () {
    // All heavy work (opening and processing big files for example) should be done inside here.
    // This way it will happen only once and the result will be shared between all VUs, saving time and memory.
    const f = JSON.parse(open('./matches.json'))
    return f; // f must be an array
});

function randomNumber(from, to){
    return Math.random().toString().slice(from, to);
}

function getRandomFromArray(array, key){
  return array[Math.floor(Math.random() * array.length)][key]
}

function randomEmail(){
    return getRandomFromArray(names, 'nombre') + randomNumber(2, 5) + '@gmail.com';
}

function randomMatchId(){
  return getRandomFromArray(matchesID, 'id')
}


export default () => {

  var body =JSON.stringify({"phoneNumber":randomNumber(2,12), "email":randomEmail()})  
  
  //sleep(5)

  const res = http.post(`${BASE_URL}/matches/${randomMatchId()}/players`,  body, headers);
  
  check(res, { 'Players created correctly': (r) => r.status === 201 })
  check(res, { 'Match is full': (r) => r.status === 409 })
   
  //sleep(1);
};