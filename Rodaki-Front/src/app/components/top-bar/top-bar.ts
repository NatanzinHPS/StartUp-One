import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-top-bar',
  imports: [],
  templateUrl: './top-bar.html',
  styleUrl: './top-bar.scss',
})
export class TopBar {

  @Input() profileImage: string | undefined = 'assets/avatar-placeholder.png';
  @Input() redirectTo: string = '/dashboard';
  @Input() title: string = 'Rodaki';
  @Output() onProfileClick = new EventEmitter<void>();

  constructor(private router: Router) { }

  goHome(): void {
    if (this.redirectTo) this.router.navigate([this.redirectTo]);
  }

  profileClicked(): void {
    this.onProfileClick.emit();
  }

}
