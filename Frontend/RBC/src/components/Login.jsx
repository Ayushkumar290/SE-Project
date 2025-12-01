import React, { useState } from 'react';

function Login() {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [otp, setOtp] = useState('');
  const [showOtpInput, setShowOtpInput] = useState(false);

  // Send OTP to phone number
  async function sendOtp() {
    const response = await fetch('/auth/send-otp', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({ phoneNumber }),
    });

    if (response.ok) {
      alert('OTP sent successfully');
      setShowOtpInput(true);
    } else {
      alert('Failed to send OTP');
    }
  }

  // Verify OTP and get JWT token
  async function verifyOtp() {
    const response = await fetch('/auth/verify-otp', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({ phoneNumber, otp }),
    });

    if (response.ok) {
      const token = await response.text();
      localStorage.setItem('jwtToken', token);
      alert('Login successful');
      setShowOtpInput(false);
    } else {
      alert('Invalid OTP');
    }
  }

  // Redirect to Google OAuth login
  function googleLogin() {
    window.location.href = '/oauth2/authorization/google';
  }

  return (
    <div className="max-w-md mx-auto p-4">
      <h2 className="text-xl font-bold mb-4">Login with Phone OTP</h2>

      <input
        type="text"
        placeholder="Phone number"
        value={phoneNumber}
        onChange={(e) => setPhoneNumber(e.target.value)}
        className="border p-2 rounded w-full mb-2"
      />
      {!showOtpInput && (
        <button onClick={sendOtp} className="bg-blue-500 text-white px-4 py-2 rounded">
          Send OTP
        </button>
      )}

      {showOtpInput && (
        <div>
          <input
            type="text"
            placeholder="Enter OTP"
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
            className="border p-2 rounded w-full mb-2 mt-2"
          />
          <button onClick={verifyOtp} className="bg-green-500 text-white px-4 py-2 rounded">
            Verify OTP
          </button>
        </div>
      )}

      <hr className="my-4" />

      <h2 className="text-xl font-bold mb-4">Or Login with Google</h2>
      <button onClick={googleLogin} className="bg-red-500 text-white px-4 py-2 rounded">
        Login with Google
      </button>
    </div>
  );
}

export default Login;
