import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bottom-nav',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './bottom-nav.html',
  styleUrl: './bottom-nav.scss',
})

export class BottomNav {
  constructor(private router: Router) { }
  go(path: string) { this.router.navigate([path]); }
  isActive(path: string) { return this.router.url.startsWith(path); }
}
