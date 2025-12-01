    // src/utils/api.js
import { AuthContext } from '../contexts/AuthContext';
import React from 'react';

export async function fetchWithAuth(url, options = {}) {
  const token = localStorage.getItem('jwtToken');
  const headers = options.headers || {};
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }
  options.headers = headers;
  return fetch(url, options);
}
