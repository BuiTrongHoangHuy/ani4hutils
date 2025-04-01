import axios from "axios";
const getToken = () => {
    console.log("gettoken: ", localStorage.getItem("token"));
    return localStorage.getItem("token");
};
// Create an Axios instance
const axiosInstance = axios.create({
    baseURL: "https://api.ani4h.site",
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
