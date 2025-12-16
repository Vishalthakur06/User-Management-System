import React from "react";

const Hero = () => (
  <section className="relative pt-16 min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-50 to-gray-100 dark:from-gray-900 dark:to-gray-800 overflow-hidden">
    <div className="absolute inset-0 overflow-hidden">
      <div className="absolute -top-40 -right-40 w-96 h-96 rounded-full bg-purple-500/30 blur-3xl"></div>
      <div className="absolute top-20 -left-20 w-72 h-72 rounded-full bg-indigo-500/30 blur-3xl"></div>
    </div>

    <div className="container mx-auto px-4 relative z-10 text-center">
      <h1 className="text-5xl md:text-7xl font-extrabold mb-6 bg-clip-text text-transparent bg-gradient-to-r from-indigo-600 via-purple-600 to-pink-600 dark:from-indigo-400 dark:via-purple-400 dark:to-pink-400 animate-gradient">
        User Management <br /> Simplified
      </h1>
      <p className="text-xl md:text-2xl text-gray-600 dark:text-gray-300 mb-10 max-w-3xl mx-auto leading-relaxed">
        Experience the next generation of user administration. Secure, fast, and intuitively designed for your workflow.
      </p>
      <div className="flex flex-col sm:flex-row justify-center gap-4">
        <button className="px-8 py-4 rounded-xl bg-gradient-to-r from-indigo-600 to-purple-600 text-white font-bold text-lg shadow-lg hover:shadow-xl transform hover:-translate-y-1 transition-all duration-200">
          Get Started
        </button>
        <button className="px-8 py-4 rounded-xl bg-white dark:bg-gray-800 text-gray-800 dark:text-white font-bold text-lg shadow-md hover:shadow-lg border border-gray-200 dark:border-gray-700 transform hover:-translate-y-1 transition-all duration-200">
          Learn More
        </button>
      </div>
    </div>
  </section>
);

export default Hero;
