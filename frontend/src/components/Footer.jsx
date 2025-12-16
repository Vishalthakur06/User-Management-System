import React from "react";

const Footer = () => (
  <footer className="bg-white dark:bg-gray-900 border-t border-gray-200 dark:border-gray-800 py-8 transition-colors duration-300">
    <div className="container mx-auto px-4 text-center">
      <p className="text-gray-600 dark:text-gray-400">
        &copy; {new Date().getFullYear()} User Management System. All rights reserved.
      </p>
      <div className="mt-4 flex justify-center space-x-6">
        <a href="#" className="text-gray-500 hover:text-indigo-600 dark:hover:text-indigo-400 transition-colors">Privacy Policy</a>
        <a href="#" className="text-gray-500 hover:text-indigo-600 dark:hover:text-indigo-400 transition-colors">Terms of Service</a>
        <a href="#" className="text-gray-500 hover:text-indigo-600 dark:hover:text-indigo-400 transition-colors">Contact</a>
      </div>
    </div>
  </footer>
);

export default Footer;
