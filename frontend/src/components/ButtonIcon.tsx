import React from "react";

interface ButtonIconProps {
  children: React.ReactNode;
  variant?: "primary" | "secondary" | "danger" | "success";
  size?: "sm" | "md" | "lg";
  onClick?: () => void;
  disabled?: boolean;
  type?: "button" | "submit" | "reset";
  className?: string;
  title?: string;
}

const ButtonIcon: React.FC<ButtonIconProps> = ({
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
    "font-medium rounded-lg transition-colors focus:outline-none focus:ring-2 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed border-2 bg-transparent flex items-center justify-center";

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
    sm: "w-6 h-6 p-1",
    md: "w-8 h-8 p-1.5",
    lg: "w-10 h-10 p-2",
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

export default ButtonIcon;
