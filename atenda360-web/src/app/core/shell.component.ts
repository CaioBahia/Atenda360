import { Component, signal } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-shell',
  imports: [RouterOutlet, RouterLink, RouterLinkActive, MatIconModule, MatTooltipModule],
  template: `
    <div class="app-shell" [class.sidebar-collapsed]="collapsed()">
      <aside class="sidebar">
        <div class="brand"><span class="brand-mark"><i></i><i></i><i></i></span><span class="brand-name">Atenda<span>360</span></span></div>
        <nav aria-label="Navegação principal">
          @for (item of navItems; track item.path) {
            <a [routerLink]="item.path" routerLinkActive="active" [matTooltip]="collapsed() ? item.label : ''" matTooltipPosition="right">
              <mat-icon>{{ item.icon }}</mat-icon><span>{{ item.label }}</span>
            </a>
          }
        </nav>
        <div class="sidebar-footer">
          <div class="help-card"><mat-icon>auto_awesome</mat-icon><strong>Precisa de ajuda?</strong><small>Acesse a central de suporte</small><button>Falar com suporte</button></div>
          <a routerLink="/configuracoes" routerLinkActive="active"><mat-icon>settings</mat-icon><span>Configurações</span></a>
        </div>
      </aside>
      <section class="main-area">
        <header class="topbar">
          <button class="icon-button menu-toggle" (click)="collapsed.set(!collapsed())" aria-label="Alternar menu"><mat-icon>menu</mat-icon></button>
          <div class="workspace"><span>Workspace</span><strong>Clínica Plena</strong><mat-icon>expand_more</mat-icon></div>
          <div class="top-actions">
            <button class="icon-button" aria-label="Pesquisar"><mat-icon>search</mat-icon></button>
            <button class="icon-button notification" aria-label="Notificações"><mat-icon>notifications_none</mat-icon><i></i></button>
            <div class="profile"><span class="avatar">CS</span><div><strong>Camila Souza</strong><small>Administradora</small></div><mat-icon>expand_more</mat-icon></div>
          </div>
        </header>
        <main><router-outlet /></main>
      </section>
    </div>
  `,
  styles: [`
    .app-shell{min-height:100vh;background:#f6f8fb}.sidebar{position:fixed;inset:0 auto 0 0;width:248px;background:#0b1f33;color:#d2deea;padding:24px 16px 18px;display:flex;flex-direction:column;z-index:10;transition:.25s ease}.brand{display:flex;align-items:center;gap:11px;padding:0 10px 30px}.brand-mark{width:31px;height:31px;display:grid;grid-template-columns:repeat(3,1fr);align-items:end;gap:3px;transform:rotate(10deg)}.brand-mark i{display:block;border-radius:5px;background:#2dd4bf}.brand-mark i:nth-child(1){height:14px;opacity:.65}.brand-mark i:nth-child(2){height:23px}.brand-mark i:nth-child(3){height:30px;background:#60a5fa}.brand-name{font-size:21px;font-weight:750;color:#fff;letter-spacing:-.7px}.brand-name span{color:#2dd4bf}nav{display:grid;gap:5px}nav a,.sidebar-footer>a{height:46px;padding:0 13px;display:flex;align-items:center;gap:13px;color:#9fb0c2;text-decoration:none;border-radius:11px;font-size:14px;font-weight:550;transition:.2s}nav a:hover,.sidebar-footer>a:hover{color:#fff;background:#132b43}nav a.active{background:linear-gradient(100deg,#153c55,#12354d);color:#fff;box-shadow:inset 3px 0 #2dd4bf}.mat-icon{font-size:21px;width:21px;height:21px}.sidebar-footer{margin-top:auto;display:grid;gap:12px}.help-card{background:linear-gradient(145deg,#13324b,#173c55);border:1px solid rgba(255,255,255,.06);padding:15px;border-radius:14px;display:grid;gap:5px}.help-card .mat-icon{color:#2dd4bf;margin-bottom:5px}.help-card strong{color:#fff;font-size:13px}.help-card small{font-size:11px;color:#94a9bc}.help-card button{border:0;background:#2dd4bf;color:#06251f;border-radius:8px;padding:8px;font-weight:700;font-size:11px;margin-top:7px;cursor:pointer}.main-area{padding-left:248px;transition:.25s}.topbar{height:72px;background:#fff;border-bottom:1px solid #e8edf3;display:flex;align-items:center;padding:0 30px;position:sticky;top:0;z-index:5}.icon-button{border:0;background:transparent;border-radius:10px;width:40px;height:40px;display:grid;place-items:center;color:#64748b;cursor:pointer}.icon-button:hover{background:#f1f5f9}.menu-toggle{margin-right:16px}.workspace{display:flex;align-items:center;gap:8px}.workspace span{font-size:12px;color:#94a3b8}.workspace strong{font-size:13px;color:#203247}.workspace .mat-icon{font-size:17px}.top-actions{margin-left:auto;display:flex;align-items:center;gap:4px}.notification{position:relative}.notification i{position:absolute;right:8px;top:8px;width:7px;height:7px;background:#f97316;border:2px solid #fff;border-radius:50%}.profile{display:flex;align-items:center;gap:10px;margin-left:13px;padding-left:16px;border-left:1px solid #e7ecf2}.avatar{width:36px;height:36px;border-radius:10px;background:linear-gradient(145deg,#d9f7f1,#b9eee5);color:#087569;display:grid;place-items:center;font-weight:750;font-size:12px}.profile div{display:grid}.profile strong{font-size:12px;color:#26374a}.profile small{font-size:10px;color:#8b9bad}.profile>.mat-icon{font-size:17px;color:#94a3b8}main{padding:28px 30px;max-width:1600px;margin:0 auto}.sidebar-collapsed .sidebar{width:78px}.sidebar-collapsed .brand-name,.sidebar-collapsed nav span,.sidebar-collapsed .help-card,.sidebar-collapsed .sidebar-footer>a span{display:none}.sidebar-collapsed .brand{padding-left:6px}.sidebar-collapsed nav a,.sidebar-collapsed .sidebar-footer>a{justify-content:center}.sidebar-collapsed .main-area{padding-left:78px}
    @media(max-width:860px){.sidebar{transform:translateX(-100%)}.sidebar-collapsed .sidebar{transform:translateX(0);width:248px;box-shadow:12px 0 30px #0b1f3340}.sidebar-collapsed .brand-name,.sidebar-collapsed nav span,.sidebar-collapsed .help-card,.sidebar-collapsed .sidebar-footer>a span{display:initial}.sidebar-collapsed nav a,.sidebar-collapsed .sidebar-footer>a{justify-content:flex-start}.main-area,.sidebar-collapsed .main-area{padding-left:0}.topbar{padding:0 14px}.workspace span,.profile div,.profile>.mat-icon{display:none}main{padding:20px 16px}}
  `],
})
export class ShellComponent {
  collapsed = signal(false);
  navItems = [
    { label: 'Visão geral', icon: 'grid_view', path: '/dashboard' },
    { label: 'Agenda', icon: 'calendar_today', path: '/agenda' },
    { label: 'Clientes', icon: 'group', path: '/clientes' },
    { label: 'Atendimentos', icon: 'forum', path: '/atendimentos' },
  ];
}
