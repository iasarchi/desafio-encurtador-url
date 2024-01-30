import { TestBed } from '@angular/core/testing';

import { EncurtadorService } from './encurtador.service';

describe('EncurtadorService', () => {
  let service: EncurtadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EncurtadorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
