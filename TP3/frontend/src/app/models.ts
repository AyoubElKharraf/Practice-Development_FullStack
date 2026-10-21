export interface MenuItem { id?: number; name: string; description: string; price: number; category: string; available: boolean; }
export interface Employee { id?: number; firstName: string; lastName: string; role: string; email: string; phone: string; }
export type OrderStatus = 'PENDING' | 'IN_PROGRESS' | 'DELIVERED' | 'CANCELLED';
export interface OrderItem { id?: number; menuItem: MenuItem; quantity: number; }
export interface RestaurantOrder { id?: number; orderDate: string; status: OrderStatus; assignedEmployee?: Employee; items: OrderItem[]; }
export interface OrderPayload { orderDate: string; status: OrderStatus; employeeId?: number; items: { menuItemId: number; quantity: number }[]; }
