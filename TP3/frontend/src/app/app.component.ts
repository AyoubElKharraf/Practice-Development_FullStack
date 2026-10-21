import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from './api.service';
import { Employee, MenuItem, OrderPayload, OrderStatus, RestaurantOrder } from './models';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  private readonly api = inject(ApiService);
  readonly today = new Date();
  tab: 'menu' | 'employees' | 'orders' = 'menu';
  menuItems: MenuItem[] = []; employees: Employee[] = []; orders: RestaurantOrder[] = [];
  menuForm: MenuItem = this.emptyMenu(); employeeForm: Employee = this.emptyEmployee();
  editingMenuId?: number; editingEmployeeId?: number; message = ''; error = '';
  orderStatus: OrderStatus = 'PENDING'; selectedEmployeeId?: number; selectedMenuId?: number; quantity = 1;
  orderLines: { menuItemId: number; quantity: number }[] = []; statuses: OrderStatus[] = ['PENDING', 'IN_PROGRESS', 'DELIVERED', 'CANCELLED'];

  ngOnInit() { this.refresh(); }
  refresh() {
    this.api.menu().subscribe({ next: x => this.menuItems = x, error: e => this.fail(e) });
    this.api.employees().subscribe({ next: x => this.employees = x, error: e => this.fail(e) });
    this.api.orders().subscribe({ next: x => this.orders = x, error: e => this.fail(e) });
  }
  saveMenu() {
    const request = this.editingMenuId ? this.api.updateMenu(this.editingMenuId, this.menuForm) : this.api.createMenu(this.menuForm);
    request.subscribe({ next: () => { this.done('Plat enregistré'); this.resetMenu(); this.refresh(); }, error: e => this.fail(e) });
  }
  editMenu(item: MenuItem) { this.editingMenuId = item.id; this.menuForm = { ...item }; }
  deleteMenu(id?: number) { if (id) this.api.deleteMenu(id).subscribe({ next: () => this.refresh(), error: e => this.fail(e) }); }
  saveEmployee() {
    const request = this.editingEmployeeId ? this.api.updateEmployee(this.editingEmployeeId, this.employeeForm) : this.api.createEmployee(this.employeeForm);
    request.subscribe({ next: () => { this.done('Employé enregistré'); this.resetEmployee(); this.refresh(); }, error: e => this.fail(e) });
  }
  editEmployee(item: Employee) { this.editingEmployeeId = item.id; this.employeeForm = { ...item }; }
  deleteEmployee(id?: number) { if (id) this.api.deleteEmployee(id).subscribe({ next: () => this.refresh(), error: e => this.fail(e) }); }
  addLine() { if (this.selectedMenuId && this.quantity > 0) { this.orderLines.push({ menuItemId: this.selectedMenuId, quantity: this.quantity }); this.quantity = 1; } }
  removeLine(index: number) { this.orderLines.splice(index, 1); }
  saveOrder() {
    const payload: OrderPayload = { orderDate: new Date().toISOString().slice(0, 19), status: this.orderStatus, employeeId: this.selectedEmployeeId, items: this.orderLines };
    this.api.createOrder(payload).subscribe({ next: () => { this.done('Commande créée'); this.orderLines = []; this.refresh(); }, error: e => this.fail(e) });
  }
  deleteOrder(id?: number) { if (id) this.api.deleteOrder(id).subscribe({ next: () => this.refresh(), error: e => this.fail(e) }); }
  label(status: OrderStatus) { return ({ PENDING: 'En attente', IN_PROGRESS: 'En cours', DELIVERED: 'Livrée', CANCELLED: 'Annulée' } as Record<OrderStatus, string>)[status]; }
  menuName(id: number) { return this.menuItems.find(item => item.id === id)?.name ?? 'Plat supprimé'; }
  total(order: RestaurantOrder) { return order.items.reduce((sum, item) => sum + item.menuItem.price * item.quantity, 0); }
  resetMenu() { this.menuForm = this.emptyMenu(); this.editingMenuId = undefined; }
  resetEmployee() { this.employeeForm = this.emptyEmployee(); this.editingEmployeeId = undefined; }
  private emptyMenu(): MenuItem { return { name: '', description: '', price: 0, category: '', available: true }; }
  private emptyEmployee(): Employee { return { firstName: '', lastName: '', role: '', email: '', phone: '' }; }
  private done(text: string) { this.message = text; this.error = ''; setTimeout(() => this.message = '', 3000); }
  private fail(error: { error?: { message?: string } }) { this.error = error.error?.message ?? 'Une erreur est survenue'; this.message = ''; }
}
