import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EncurtadorComponent } from './encurtador.component';

describe('EncurtadorComponent', () => {
  let component: EncurtadorComponent;
  let fixture: ComponentFixture<EncurtadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EncurtadorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EncurtadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
