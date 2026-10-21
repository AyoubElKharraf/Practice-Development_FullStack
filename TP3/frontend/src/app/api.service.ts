import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Employee, MenuItem, OrderPayload, RestaurantOrder } from './models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly http = inject(HttpClient);
  private readonly base = 'http://localhost:8081/api';
  menu() { return this.http.get<MenuItem[]>(`${this.base}/menu-items`); }
  createMenu(item: MenuItem) { return this.http.post<MenuItem>(`${this.base}/menu-items`, item); }
  updateMenu(id: number, item: MenuItem) { return this.http.put<MenuItem>(`${this.base}/menu-items/${id}`, item); }
  deleteMenu(id: number) { return this.http.delete<void>(`${this.base}/menu-items/${id}`); }
  employees() { return this.http.get<Employee[]>(`${this.base}/employees`); }
  createEmployee(item: Employee) { return this.http.post<Employee>(`${this.base}/employees`, item); }
  updateEmployee(id: number, item: Employee) { return this.http.put<Employee>(`${this.base}/employees/${id}`, item); }
  deleteEmployee(id: number) { return this.http.delete<void>(`${this.base}/employees/${id}`); }
  orders() { return this.http.get<RestaurantOrder[]>(`${this.base}/orders`); }
  createOrder(order: OrderPayload) { return this.http.post<RestaurantOrder>(`${this.base}/orders`, order); }
  updateOrder(id: number, order: OrderPayload) { return this.http.put<RestaurantOrder>(`${this.base}/orders/${id}`, order); }
  deleteOrder(id: number) { return this.http.delete<void>(`${this.base}/orders/${id}`); }
}
