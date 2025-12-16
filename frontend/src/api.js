import axios from "axios";

const API_URL =  "http://localhost:8080/api";

export const registerUser = (data) =>
  axios.post(`${API_URL}/auth/register`, data);
export const loginUser = (data) => axios.post(`${API_URL}/auth/login`, data);
export const getProfile = (token) =>
  axios.get(`${API_URL}/user/profile`, {
    headers: { Authorization: `Bearer ${token}` },
  });
export const updateProfile = (token, data) =>
  axios.put(`${API_URL}/user/profile`, data, {
    headers: { Authorization: `Bearer ${token}` },
  });
