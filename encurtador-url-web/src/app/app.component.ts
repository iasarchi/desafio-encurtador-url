import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {EncurtadorComponent} from "./pages/encurtador/encurtador.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, EncurtadorComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'encurtador-url-web';

}
