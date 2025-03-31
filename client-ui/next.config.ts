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
            }
        ],
    },
};

export default nextConfig;
