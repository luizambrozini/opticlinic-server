import React from "react";

interface ButtonProps {
  children: React.ReactNode;
  variant?: "primary" | "secondary" | "danger" | "success";
  size?: "sm" | "md" | "lg";
  onClick?: () => void;
  disabled?: boolean;
  type?: "button" | "submit" | "reset";
  className?: string;
  title?: string;
}

const Button: React.FC<ButtonProps> = ({
  children,
  variant = "primary",
  size = "md",
  onClick,
  disabled = false,
  type = "button",
  className = "",
  title,
}) => {
  const baseClasses =
    "min-w-[70px] min-h-[30px] font-medium rounded-lg transition-colors focus:outline-none focus:ring-2 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed border-2 bg-transparent text-[10px]";

  const variantClasses = {
    primary:
      "border-blue-600 text-blue-600 hover:bg-blue-50 focus:ring-blue-500",
    secondary:
      "border-gray-600 text-gray-600 hover:bg-gray-50 focus:ring-gray-500",
    danger: "border-red-600 text-red-600 hover:bg-red-50 focus:ring-red-500",
    success:
      "border-green-600 text-green-600 hover:bg-green-50 focus:ring-green-500",
  };

  const sizeClasses = {
    sm: "px-2 py-1",
    md: "px-3 py-1.5",
    lg: "px-4 py-2",
  };

  const classes =
    `${baseClasses} ${variantClasses[variant]} ${sizeClasses[size]} ${className}`.trim();

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={classes}
      title={title}
    >
      {children}
    </button>
  );
};

export default Button;
