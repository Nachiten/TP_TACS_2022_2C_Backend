import http from 'k6/http';
import { check, group, sleep, fail } from 'k6';


export const options = {
  iterations:100,
  //duration: '1m',
  //stages: [{ target: 100, duration: '30s' }],
  vus: 100,
  thresholds: {
    'http_req_duration': ['p(75)<1000', 'p(50)<2000'],
    'http_req_duration': ['avg<400'],
    'http_req_duration': ['avg<600', 'max<1000'],
  },
};

function randomNumber(from, to){
    return Math.random().toString().slice(from, to);
}

const BASE_URL = 'http://tacsutn.ddns.net/api';

const headers =  {headers: { 'Content-Type': 'application/json' }}

export default () => {
 
  var res = http.get(`${BASE_URL}/statistics/players?hours=${randomNumber(2,3)}`, headers);

  check(res, { 'Statistics of players retrieved correctly': (r) => r.status === 200 })

  sleep(8);

  res = http.get(`${BASE_URL}/statistics/matches?hours=${randomNumber(2,4)}`, headers);

  check(res, { 'Statistics of matches retrieved correctly': (r) => r.status === 200 })
   
  sleep(8);

};