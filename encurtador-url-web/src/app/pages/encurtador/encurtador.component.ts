import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {EncurtadorService} from "../../core/encurtador.service";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {ToastrModule, ToastrService} from "ngx-toastr";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInput, MatInputModule} from "@angular/material/input";

@Component({
  selector: 'app-encurtador',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatTableModule,
    FormsModule, MatFormFieldModule, MatInputModule, MatButton
  ],
  templateUrl: './encurtador.component.html',
  styleUrl: './encurtador.component.css'
})
export class EncurtadorComponent implements OnInit {

  urlForm = this.formBuilder.group({
    url: '',
    customAlias: ''
  });

  topTenUrlList: any[] = []

  urlList: any[] = []

  constructor(
    private formBuilder: FormBuilder,
    private encurtadorService: EncurtadorService,
    private toastrService: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.loadTopTenUrls();
    if (localStorage.getItem('urlList')) {
      this.urlList = JSON.parse(localStorage.getItem('urlList')!);
    }
  }

  onSubmit(): void {
    console.log("funcionando Submit")
    let urlValue = this.urlForm.get('url')?.value;
    let customAliasValue = this.urlForm.get('customAlias')?.value;

    this.encurtadorService.shortUrl(urlValue!, customAliasValue!).subscribe(response => {
      console.log('Resposta API', response)
      this.urlList = [response, ...this.urlList];
      localStorage.setItem('urlList', JSON.stringify(this.urlList));
      this.urlForm.reset();
    });
  }

  loadTopTenUrls() {
    this.encurtadorService.getTopTenUrl().subscribe(response => {
      console.log('Top ten', response)
      this.topTenUrlList = response;
    })
  }
}
