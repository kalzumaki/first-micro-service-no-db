<?php

namespace App\Http\Controllers;

use App\Models\User;

class UserController extends Controller{

    public function helloWorld()
    {
        return response()->json(['message' => 'Hello, World!']);
    }

    public function show($id){
        $users = [
            1 => new User(1, 'John Nigger'),
            2 => new User(2, 'Tung Tung Sahur'),
            3 => new User(3, 'Brr Brr Patapim')
        ];

        if (array_key_exists($id, $users)) {
            return response()->json($users[$id]);
        } 
        
        return response()->json(['message' => 'User not found'], 404);
    }
}