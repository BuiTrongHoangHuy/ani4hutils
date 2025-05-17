import type { NextConfig } from "next";

const nextConfig: NextConfig = {
    output: "standalone",
    images: {
        remotePatterns: [
            {
                protocol: 'https',
                hostname: 'cdn.myanimelist.net',
                port: '',
                pathname: '/images/**',
                search: '',
            },
            {
                protocol: "https",
                hostname: "placehold.co",
                pathname: '/**'
            },
            {
                protocol: "https",
                hostname: "img.daisyui.com",
                pathname: '/**'
            },
            {
                protocol: "https",
                hostname: "cdn.myanimelist.net",
                pathname: '/**'
            }
        ],
    },
};

export default nextConfig;
