import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PassengerManagement } from './passenger-management';

describe('PassengerManagement', () => {
  let component: PassengerManagement;
  let fixture: ComponentFixture<PassengerManagement>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PassengerManagement]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PassengerManagement);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
