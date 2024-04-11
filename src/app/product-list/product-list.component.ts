import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.component.html',
})
export class ProductListComponent {
  products: Product[];

  constructor(private productService: ProductService, private router: Router) {}

  ngOnInit() {
    // Se cargan los productos
    this.getProducts();
  }

  private getProducts() {
    // Consumir los datos del observable (suscripción)
    this.productService.getProductsList().subscribe((data) => {
      this.products = data;
    });
  }

  editProduct(id: number) {
    this.router.navigate(['/edit-product', id]);
  }

  deleteProduct(id: number) {
    this.productService.deleteProduct(id).subscribe({
      next: (data) => this.getProducts(),
      error: (errors) => console.log(errors),
    });
  }
}
