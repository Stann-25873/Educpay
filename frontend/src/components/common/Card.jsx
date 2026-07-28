import { motion } from "framer-motion";

export function Card({
  children,
  className = "",
  hoverable = false,
  accent = null,
  padding = true,
  ...props
}) {
  const baseClasses = padding ? "p-6" : "";
  const accentClass = accent ? `card-stats ${accent}` : "";
  const hoverClass = hoverable ? "cursor-pointer hover:shadow-card-hover hover:border-edu-primary/20" : "";

  return (
    <motion.div
      initial={{ opacity: 0, y: 10 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.2 }}
      className={`rounded-2xl border border-edu-border bg-white shadow-card transition-all duration-200 ${baseClasses} ${accentClass} ${hoverClass} ${className}`}
      {...props}
    >
      {children}
    </motion.div>
  );
}

export function CardHeader({ children, className = "" }) {
  return <div className={`mb-4 ${className}`}>{children}</div>;
}

export function CardBody({ children, className = "" }) {
  return <div className={className}>{children}</div>;
}

export function CardFooter({ children, className = "" }) {
  return (
    <div className={`mt-4 flex items-center justify-between border-t border-edu-border pt-4 ${className}`}>
      {children}
    </div>
  );
}
