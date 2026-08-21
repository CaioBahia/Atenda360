import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
export const authGuard:CanActivateFn=()=>localStorage.getItem('atenda360_token')?true:inject(Router).createUrlTree(['/login']);
