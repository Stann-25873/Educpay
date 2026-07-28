import { motion } from "framer-motion";

const variants = {
  primary:
    "bg-edu-primary text-white hover:bg-edu-primary-dark focus:ring-edu-primary/30",
  secondary:
    "bg-white text-edu-text-primary ring-1 ring-inset ring-edu-border hover:bg-edu-surface-hover",
  danger: "bg-rouge-500 text-white hover:bg-rouge-600 focus:ring-rouge-500/30",
  ghost:
    "text-edu-text-secondary hover:bg-edu-surface-hover hover:text-edu-text-primary",
};

const sizes = {
  sm: "px-3 py-1.5 text-xs",
  md: "px-5 py-2.5 text-sm",
  lg: "px-6 py-3 text-base",
};

export function Button({
  children,
  variant = "primary",
  size = "md",
  className = "",
  icon: Icon,
  iconPosition = "left",
  loading = false,
  disabled = false,
  type = "button",
  ...props
}) {
  const baseClasses =
    "inline-flex items-center justify-center gap-2 rounded-xl font-semibold shadow-sm transition-all duration-200 focus:outline-none focus:ring-2 active:scale-[0.98] disabled:cursor-not-allowed disabled:opacity-50";

  const classes = [baseClasses, variants[variant], sizes[size], className]
    .filter(Boolean)
    .join(" ");

  return (
    <motion.button
      type={type}
      className={classes}
      disabled={disabled || loading}
      whileTap={!disabled && !loading ? { scale: 0.97 } : undefined}
      {...props}
    >
      {loading && (
        <svg
          className="h-4 w-4 animate-spin"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
        >
          <circle
            className="opacity-25"
            cx="12"
            cy="12"
            r="10"
            stroke="currentColor"
            strokeWidth="4"
          />
          <path
            className="opacity-75"
            fill="currentColor"
            d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
          />
        </svg>
      )}
      {!loading && Icon && iconPosition === "left" && <Icon className="h-4 w-4" />}
      {children}
      {!loading && Icon && iconPosition === "right" && <Icon className="h-4 w-4" />}
    </motion.button>
  );
}
