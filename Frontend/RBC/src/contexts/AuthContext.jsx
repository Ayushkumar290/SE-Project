// src/contexts/AuthContext.js
import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [jwtToken, setJwtToken] = useState(localStorage.getItem('jwtToken'));
  
  // Call this to login (save token)
  const login = (token) => {
    localStorage.setItem('jwtToken', token);
    setJwtToken(token);
  };

  // Logout clears token
  const logout = () => {
    localStorage.removeItem('jwtToken');
    setJwtToken(null);
  };

  return (
    <AuthContext.Provider value={{ jwtToken, login, logout, isAuthenticated: !!jwtToken }}>
      {children}
    </AuthContext.Provider>
  );
};
