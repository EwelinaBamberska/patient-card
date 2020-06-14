import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserVersionsComponent } from './user-versions.component';

describe('UserVersionsComponent', () => {
  let component: UserVersionsComponent;
  let fixture: ComponentFixture<UserVersionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserVersionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserVersionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
