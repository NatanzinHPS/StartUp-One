import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PassengerHome } from './passenger-home';

describe('PassengerHome', () => {
  let component: PassengerHome;
  let fixture: ComponentFixture<PassengerHome>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PassengerHome]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PassengerHome);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
