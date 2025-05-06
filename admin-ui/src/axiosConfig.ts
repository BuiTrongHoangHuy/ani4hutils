import axios from "axios";
const getToken = () => {
    return localStorage.getItem("token");
};
// Create an Axios instance
const axiosInstance = axios.create({
    baseURL: "http://localhost:4002",
    timeout: 5000,
    headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getToken() || ""}`, // Initial token
    },
});

axiosInstance.interceptors.request.use(
    (config) => {
        config.headers.Authorization = `Bearer ${getToken() || ""}`;
        return config;
    },
    (error) => {
        // Handle request errors
        return Promise.reject(error);
    },
);

axiosInstance.interceptors.response.use(
    (response) => {
        // Do something with successful responses
        return response;
    },
    (error) => {
        // Handle response errors
        return Promise.reject(error);
    },
);
export default axiosInstance;

export const axiosInstance2 = axios.create({
    baseURL: "http://localhost:4001",
    timeout: 5000,
    headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getToken() || ""}`, // Initial token
    },
});

axiosInstance2.interceptors.request.use(
    (config) => {
        config.headers.Authorization = `Bearer ${getToken() || ""}`;
        return config;
    },
    (error) => {
        // Handle request errors
        return Promise.reject(error);
    },
);

axiosInstance2.interceptors.response.use(
    (response) => {
        // Do something with successful responses
        return response;
    },
    (error) => {
        // Handle response errors
        return Promise.reject(error);
    },
);
