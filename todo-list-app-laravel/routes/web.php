<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UserController;
use App\Http\Middleware\AddCorsHeaders;

// Apply CORS middleware to specific routes
Route::get('/hello-world', [UserController::class, 'helloWorld'])->middleware(AddCorsHeaders::class);
Route::get('/users/{id}', [UserController::class, 'show'])->middleware(AddCorsHeaders::class);

// Keep your OPTIONS route for preflight
Route::options('/{any}', function () {
    return response('', 200)
        ->header('Access-Control-Allow-Origin', '*')
        ->header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
        ->header('Access-Control-Allow-Headers', 'Content-Type, Authorization, X-Requested-With, X-CSRF-TOKEN');
})->where('any', '.*');

