import { Routes } from '@angular/router';
import { authGuard } from './core/auth.guard';

export const routes: Routes = [
  { path: 'login', loadComponent: () => import('./features/auth/login.component').then(m => m.LoginComponent) },
  {
    path: '', loadComponent: () => import('./core/shell.component').then(m => m.ShellComponent), canActivate: [authGuard], children: [
      { path: 'dashboard', loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent), title: 'Visão geral | Atenda360' },
      { path: 'agenda', loadComponent: () => import('./features/agenda/agenda.component').then(m => m.AgendaComponent), title: 'Agenda | Atenda360' },
      { path: 'clientes', loadComponent: () => import('./features/clientes/clientes.component').then(m => m.ClientesComponent), title: 'Clientes | Atenda360' },
      { path: 'atendimentos', loadComponent: () => import('./features/atendimentos/atendimentos.component').then(m => m.AtendimentosComponent), title: 'Atendimentos | Atenda360' },
      { path: 'configuracoes', loadComponent: () => import('./features/configuracoes/configuracoes.component').then(m => m.ConfiguracoesComponent), title: 'Configurações | Atenda360' },
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
    ],
  },
  { path: '**', redirectTo: '' },
];
