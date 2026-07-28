/**
 * Format a date to a human-readable string.
 * @param {Date|string|number} date
 * @param {object} options
 * @returns {string}
 */
export function formatDate(date, options = {}) {
  if (!date) return "";
  const d = new Date(date);
  if (isNaN(d.getTime())) return String(date);

  const defaults = {
    year: "numeric",
    month: "short",
    day: "numeric",
    ...options,
  };

  return d.toLocaleDateString("en-US", defaults);
}

/**
 * Format a date with time.
 * @param {Date|string|number} date
 * @returns {string}
 */
export function formatDateTime(date) {
  if (!date) return "";
  const d = new Date(date);
  if (isNaN(d.getTime())) return String(date);

  return d.toLocaleDateString("en-US", {
    year: "numeric",
    month: "short",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
}

/**
 * Format a relative time string (e.g., "2 hours ago").
 * @param {Date|string|number} date
 * @returns {string}
 */
export function formatRelativeTime(date) {
  if (!date) return "";
  const now = new Date();
  const d = new Date(date);
  const diffMs = now - d;
  const diffSeconds = Math.floor(diffMs / 1000);
  const diffMinutes = Math.floor(diffSeconds / 60);
  const diffHours = Math.floor(diffMinutes / 60);
  const diffDays = Math.floor(diffHours / 24);
  const diffWeeks = Math.floor(diffDays / 7);
  const diffMonths = Math.floor(diffDays / 30);

  if (diffSeconds < 60) return "just now";
  if (diffMinutes < 60) return `${diffMinutes}m ago`;
  if (diffHours < 24) return `${diffHours}h ago`;
  if (diffDays < 7) return `${diffDays}d ago`;
  if (diffWeeks < 5) return `${diffWeeks}w ago`;
  if (diffMonths < 12) return `${diffMonths}mo ago`;
  return formatDate(date);
}

/**
 * Format a number as currency (XAF by default).
 * @param {number} amount
 * @param {string} currency
 * @returns {string}
 */
export function formatCurrency(amount, currency = "XAF") {
  if (amount == null || isNaN(amount)) return "—";

  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency,
    minimumFractionDigits: 0,
    maximumFractionDigits: 0,
  }).format(amount);
}

/**
 * Format a number with commas.
 * @param {number} num
 * @returns {string}
 */
export function formatNumber(num) {
  if (num == null || isNaN(num)) return "—";
  return new Intl.NumberFormat("en-US").format(num);
}

/**
 * Format a percentage.
 * @param {number} value
 * @param {number} decimals
 * @returns {string}
 */
export function formatPercent(value, decimals = 1) {
  if (value == null || isNaN(value)) return "—";
  return `${value.toFixed(decimals)}%`;
}

/**
 * Truncate a string with ellipsis.
 * @param {string} str
 * @param {number} maxLength
 * @returns {string}
 */
export function truncate(str, maxLength = 50) {
  if (!str) return "";
  if (str.length <= maxLength) return str;
  return str.substring(0, maxLength) + "...";
}

/**
 * Get initials from a name.
 * @param {string} firstName
 * @param {string} lastName
 * @returns {string}
 */
export function getInitials(firstName, lastName) {
  const first = firstName ? firstName.charAt(0).toUpperCase() : "";
  const last = lastName ? lastName.charAt(0).toUpperCase() : "";
  return `${first}${last}` || "?";
}

/**
 * Format a phone number.
 * @param {string} phone
 * @returns {string}
 */
export function formatPhone(phone) {
  if (!phone) return "";
  // Simple formatting: group digits
  const cleaned = phone.replace(/\D/g, "");
  if (cleaned.length === 9) {
    return `${cleaned.slice(0, 3)} ${cleaned.slice(3, 6)} ${cleaned.slice(6)}`;
  }
  if (cleaned.length === 12) {
    return `+${cleaned.slice(0, 3)} ${cleaned.slice(3, 6)} ${cleaned.slice(6, 9)} ${cleaned.slice(9)}`;
  }
  return phone;
}

/**
 * Convert a string to title case.
 * @param {string} str
 * @returns {string}
 */
export function toTitleCase(str) {
  if (!str) return "";
  return str
    .toLowerCase()
    .replace(/_/g, " ")
    .replace(/\b\w/g, (c) => c.toUpperCase());
}
