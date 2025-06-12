import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";
import TopBar from "@/components/layout/topbar";
import React from "react";
import Image from "next/image";
import Link from "next/link";
import {ClientInterceptorWrapper} from "@/utils/ClientInterceptorWrapper";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Ani4h",
  description: "A streaming anime website",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
      <html lang="en">
      <body
          className={`${geistSans.variable} ${geistMono.variable} antialiased`}
      >
      <div className="">
          <ClientInterceptorWrapper />
          <header className="absolute top-0 z-50 w-full">
              <TopBar/>
          </header>
          <main className={"relative"}>
              {children}
          </main>
          <footer className={"mt-20"}>
              <div className={"divider"}></div>
              <div className={"flex justify-evenly"}>
                  <div className={" space-y-2"}>
                      <p>Agreement and terms</p>
                      <p className={"opacity-50"}>Privacy policy</p>
                      <p className={"opacity-50"}>Agreement and terms</p>
                      <p className={"opacity-50"}>Cookie policy</p>
                  </div>
                  <div className={" space-y-2"}>
                      <p>Support and feedback</p>
                      <p className={"opacity-50"}>Feedback</p>
                      <p className={"opacity-50"}>Frequently asked questions</p>
                  </div>
                  <div className={" space-y-2"}>
                      <p>Information about us</p>
                      <p className={"opacity-50"}>Join us</p>
                      <p className={"opacity-50"}>Contact us</p>
                      <div className={"flex gap-4"}>
                          <Link href={"https://facebook.com"} rel="noopener noreferrer" target="_blank">
                              <Image width={35} height={35} src={"./images/icons/facebook-logo.svg"} alt={"facebook-contact"}/>
                          </Link>
                          <Link href={"https://youtube.com"} rel="noopener noreferrer" target="_blank">
                              <Image width={35} height={35} src={"./images/icons/youtube-logo.svg"} alt={"youtube-contact"}/>
                          </Link>
                          <Link href={"https://instagram.com"} rel="noopener noreferrer" target="_blank">
                              <Image width={35} height={35} src={"./images/icons/instagram-logo.svg"} alt={"instagram-contact"}/>
                          </Link>
                          <Link href={"https://x.com"} rel="noopener noreferrer" target="_blank">
                              <Image width={35} height={35} src={"./images/icons/twitter-logo.svg"} alt={"twitter-contact"}/>
                          </Link>
                      </div>
                  </div>
              </div>
              <div className={"divider"}></div>
          </footer>
      </div>
      </body>
      </html>
  );
}
