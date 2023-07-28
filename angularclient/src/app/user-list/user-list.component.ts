import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user';
import { UserService } from '../service/user.service'

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];

  constructor(private userService: UserService) {
  }

  /**
   * Uses the UserService's findAll() method to fetch all the entities persisted
   *  in the database and stores them in the users field.
   */
  ngOnInit() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
    });
  }
}
