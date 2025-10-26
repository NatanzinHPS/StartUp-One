import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DailyCheckinList } from './daily-checkin-list';

describe('DailyCheckinList', () => {
  let component: DailyCheckinList;
  let fixture: ComponentFixture<DailyCheckinList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DailyCheckinList]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DailyCheckinList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
