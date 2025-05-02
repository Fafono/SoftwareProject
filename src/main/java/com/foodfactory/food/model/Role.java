package com.foodfactory.food.model;

public enum Role {
   USER,
   ADMIN;

   Role() {
   }

   public static Role getRole(String role) {
      return Role.valueOf(role);
   }

}
