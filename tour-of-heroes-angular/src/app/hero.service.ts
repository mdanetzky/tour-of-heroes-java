import { Injectable }    from '@angular/core';
import { Jsonp, URLSearchParams } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Hero } from './hero';

@Injectable()
export class HeroService {

  private heroesUrl = 'http://localhost:8099/heroes';  // URL to web api

  constructor(private jsonp: Jsonp) { }

  getHeroes(): Promise<Hero[]> {
    return this.jsonp.get(this.heroesUrl + '?callback=JSONP_CALLBACK')
               .toPromise()
               .then(response => response.json() as Hero[])
               .catch(this.handleError);
  }

  getHero(id: number): Promise<Hero> {
    const url = `${this.heroesUrl}/${id}?callback=JSONP_CALLBACK`;
    return this.jsonp.get(url)
      .toPromise()
      .then(response => response.json() as Hero)
      .catch(this.handleError);
  }

  create(name: string): Promise<Hero> {
    let parameters: URLSearchParams = new URLSearchParams();
    parameters.set('name', name);
    parameters.set('callback', 'JSONP_CALLBACK');
    return this.jsonp
      .get(`${this.heroesUrl}/create`, {search: parameters})
      .toPromise()
      .then(res => res.json().data)
      .catch(this.handleError);
  }

  update(hero: Hero): Promise<Hero> {
    let parameters: URLSearchParams = new URLSearchParams();
    parameters.set('name', hero.name);
    parameters.set('callback', 'JSONP_CALLBACK');
    return this.jsonp
      .get(`${this.heroesUrl}/save/` + hero.id, {search: parameters})
      .toPromise()
      .then(() => hero)
      .catch(this.handleError);
  }

  delete(id: number): Promise<void> {
    let parameters: URLSearchParams = new URLSearchParams();
    parameters.set('callback', 'JSONP_CALLBACK');
    return this.jsonp
      .get(`${this.heroesUrl}/delete/` + id, {search: parameters})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
