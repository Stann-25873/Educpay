export const ROLES = {
  SUPER_ADMIN: "SUPER_ADMIN",
  SCHOOL_ADMIN: "SCHOOL_ADMIN",
  ACCOUNTANT: "ACCOUNTANT",
  TEACHER: "TEACHER",
  PARENT: "PARENT",
  STUDENT: "STUDENT",
};

export const ROLE_LABELS = {
  [ROLES.SUPER_ADMIN]: "Super Admin",
  [ROLES.SCHOOL_ADMIN]: "School Admin",
  [ROLES.ACCOUNTANT]: "Accountant",
  [ROLES.TEACHER]: "Teacher",
  [ROLES.PARENT]: "Parent",
  [ROLES.STUDENT]: "Student",
};

export const ROLE_BADGE_COLORS = {
  [ROLES.SUPER_ADMIN]: "badge-rouge",
  [ROLES.SCHOOL_ADMIN]: "badge-indigo",
  [ROLES.ACCOUNTANT]: "badge-warning",
  [ROLES.TEACHER]: "badge-vert",
  [ROLES.PARENT]: "badge-muted",
  [ROLES.STUDENT]: "badge-muted",
};

export const ROUTES = {
  LOGIN: "/login",
  FORGOT_PASSWORD: "/forgot-password",
  DASHBOARD: "/dashboard",
  USERS: "/users",
  USER_DETAIL: (id) => `/users/${id}`,
  STUDENTS: "/students",
  STUDENT_DETAIL: (id) => `/students/${id}`,
  PARENTS: "/parents",
  PARENT_DETAIL: (id) => `/parents/${id}`,
  FEES: "/fees",
  FEE_CONFIG: "/fees/config",
  PAYMENTS: "/payments",
  NEW_PAYMENT: "/payments/new",
  INVOICES: "/invoices",
  RECEIPT: (id) => `/invoices/${id}/receipt`,
  OVERDUE: "/overdue",
  REPORTS: "/reports",
  NOTIFICATIONS: "/notifications",
  SETTINGS: "/settings",
  PROFILE: "/profile",
};

export const SIDEBAR_ITEMS = [
  {
    label: "Dashboard",
    path: ROUTES.DASHBOARD,
    icon: "HiOutlineChartBar",
    roles: [ROLES.SUPER_ADMIN, ROLES.SCHOOL_ADMIN, ROLES.ACCOUNTANT, ROLES.TEACHER, ROLES.PARENT],
  },
  {
    label: "Users",
    path: ROUTES.USERS,
    icon: "HiOutlineUserGroup",
    roles: [ROLES.SUPER_ADMIN, ROLES.SCHOOL_ADMIN],
  },
  {
    label: "Students",
    path: ROUTES.STUDENTS,
    icon: "HiOutlineAcademicCap",
    roles: [ROLES.SUPER_ADMIN, ROLES.SCHOOL_ADMIN, ROLES.ACCOUNTANT, ROLES.TEACHER],
  },
  {
    label: "Parents",
    path: ROUTES.PARENTS,
    icon: "HiOutlineUserGroup",
    roles: [ROLES.SUPER_ADMIN, ROLES.SCHOOL_ADMIN, ROLES.ACCOUNTANT],
  },
  {
    label: "Fees",
    path: ROUTES.FEES,
    icon: "HiOutlineCurrencyDollar",
    roles: [ROLES.SUPER_ADMIN, ROLES.SCHOOL_ADMIN, ROLES.ACCOUNTANT],
  },
  {
    label: "Payments",
    path: ROUTES.PAYMENTS,
    icon: "HiOutlineCreditCard",
    roles: [ROLES.SUPER_ADMIN, ROLES.SCHOOL_ADMIN, ROLES.ACCOUNTANT],
  },
  {
    label: "Invoices",
    path: ROUTES.INVOICES,
    icon: "HiOutlineDocumentText",
    roles: [ROLES.SUPER_ADMIN, ROLES.SCHOOL_ADMIN, ROLES.ACCOUNTANT, ROLES.PARENT],
  },
  {
    label: "Overdue",
    path: ROUTES.OVERDUE,
    icon: "HiOutlineExclamationCircle",
    roles: [ROLES.SUPER_ADMIN, ROLES.SCHOOL_ADMIN, ROLES.ACCOUNTANT],
  },
  {
    label: "Reports",
    path: ROUTES.REPORTS,
    icon: "HiOutlineChartPie",
    roles: [ROLES.SUPER_ADMIN, ROLES.SCHOOL_ADMIN, ROLES.ACCOUNTANT],
  },
  {
    label: "Notifications",
    path: ROUTES.NOTIFICATIONS,
    icon: "HiOutlineBell",
    roles: [ROLES.SUPER_ADMIN, ROLES.SCHOOL_ADMIN, ROLES.ACCOUNTANT, ROLES.TEACHER, ROLES.PARENT],
  },
  {
    label: "Settings",
    path: ROUTES.SETTINGS,
    icon: "HiOutlineCog",
    roles: [ROLES.SUPER_ADMIN, ROLES.SCHOOL_ADMIN],
  },
];

export const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080/api";

export const PAYMENT_STATUS = {
  PENDING: "PENDING",
  COMPLETED: "COMPLETED",
  FAILED: "FAILED",
  REFUNDED: "REFUNDED",
};

export const PAYMENT_METHODS = {
  CASH: "CASH",
  CARD: "CARD",
  BANK_TRANSFER: "BANK_TRANSFER",
  MOBILE_MONEY: "MOBILE_MONEY",
  CHECK: "CHECK",
};

export const FEE_TYPE = {
  TUITION: "TUITION",
  REGISTRATION: "REGISTRATION",
  EXAM: "EXAM",
  LIBRARY: "LIBRARY",
  LABORATORY: "LABORATORY",
  SPORTS: "SPORTS",
  TRANSPORT: "TRANSPORT",
  OTHER: "OTHER",
};

export const INVOICE_STATUS = {
  DRAFT: "DRAFT",
  SENT: "SENT",
  PAID: "PAID",
  PARTIALLY_PAID: "PARTIALLY_PAID",
  OVERDUE: "OVERDUE",
  CANCELLED: "CANCELLED",
};

export const NOTIFICATION_TYPES = {
  PAYMENT_RECEIVED: "PAYMENT_RECEIVED",
  INVOICE_DUE: "INVOICE_DUE",
  OVERDUE_REMINDER: "OVERDUE_REMINDER",
  STUDENT_ENROLLED: "STUDENT_ENROLLED",
  ACCOUNT_CREATED: "ACCOUNT_CREATED",
  SYSTEM: "SYSTEM",
};
