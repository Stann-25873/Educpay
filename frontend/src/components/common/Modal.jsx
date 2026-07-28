import { motion, AnimatePresence } from "framer-motion";
import { HiOutlineX } from "react-icons/hi";
import { useEffect } from "react";

export function Modal({ isOpen, onClose, title, children, size = "md", className = "" }) {
  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "unset";
    }
    return () => {
      document.body.style.overflow = "unset";
    };
  }, [isOpen]);

  const sizeClasses = {
    sm: "max-w-md",
    md: "max-w-lg",
    lg: "max-w-2xl",
    xl: "max-w-4xl",
  };

  return (
    <AnimatePresence>
      {isOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="absolute inset-0 bg-black/50 backdrop-blur-sm"
            onClick={onClose}
          />
          <motion.div
            initial={{ opacity: 0, scale: 0.95, y: 20 }}
            animate={{ opacity: 1, scale: 1, y: 0 }}
            exit={{ opacity: 0, scale: 0.95, y: 20 }}
            transition={{ type: "spring", damping: 25, stiffness: 300 }}
            className={`relative w-full ${sizeClasses[size]} rounded-2xl bg-white shadow-modal ${className}`}
          >
            {title && (
              <div className="flex items-center justify-between border-b border-edu-border px-6 py-4">
                <h2 className="text-lg font-semibold text-edu-text-primary">{title}</h2>
                <button
                  onClick={onClose}
                  className="rounded-lg p-1.5 text-edu-text-secondary hover:bg-edu-surface-hover hover:text-edu-text-primary transition-colors"
                >
                  <HiOutlineX className="h-5 w-5" />
                </button>
              </div>
            )}
            <div className="p-6">{children}</div>
          </motion.div>
        </div>
      )}
    </AnimatePresence>
  );
}
