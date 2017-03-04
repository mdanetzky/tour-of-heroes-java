import { Injectable } from '@angular/core';
import { Jsonp }       from '@angular/http';
import { Observable }     from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { Hero }           from './hero';

@Injectable()
export class HeroSearchService {

  constructor(private jsonp: Jsonp) {}

  search(term: string): Observable<Hero[]> {
    return this.jsonp
               .get(`http://localhost:8099/heroes/search/${term}?callback=JSONP_CALLBACK`)
               .map(response => response.json() as Hero[]);
  }
}
