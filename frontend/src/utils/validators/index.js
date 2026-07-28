import { z } from "zod";

export const loginSchema = z.object({
  email: z
    .string()
    .min(1, "Email is required")
    .email("Invalid email address"),
  password: z
    .string()
    .min(1, "Password is required")
    .min(8, "Password must be at least 8 characters"),
});

export const forgotPasswordSchema = z.object({
  email: z
    .string()
    .min(1, "Email is required")
    .email("Invalid email address"),
});

export const createUserSchema = z.object({
  email: z
    .string()
    .min(1, "Email is required")
    .email("Invalid email address"),
  password: z
    .string()
    .min(8, "Password must be at least 8 characters")
    .regex(/[A-Z]/, "Password must contain at least one uppercase letter")
    .regex(/[a-z]/, "Password must contain at least one lowercase letter")
    .regex(/[0-9]/, "Password must contain at least one number"),
  firstName: z.string().min(1, "First name is required").max(50),
  lastName: z.string().min(1, "Last name is required").max(50),
  phone: z.string().optional(),
  roleId: z.string().uuid("Invalid role").optional(),
});

export const createStudentSchema = z.object({
  firstName: z.string().min(1, "First name is required").max(50),
  lastName: z.string().min(1, "Last name is required").max(50),
  dateOfBirth: z.string().optional(),
  gender: z.enum(["MALE", "FEMALE", "OTHER"]).optional(),
  address: z.string().optional(),
  phone: z.string().optional(),
  email: z.string().email("Invalid email").optional().or(z.literal("")),
  enrollmentDate: z.string().optional(),
  grade: z.string().optional(),
  parentIds: z.array(z.string().uuid()).optional(),
});

export const createParentSchema = z.object({
  firstName: z.string().min(1, "First name is required").max(50),
  lastName: z.string().min(1, "Last name is required").max(50),
  email: z.string().email("Invalid email").optional().or(z.literal("")),
  phone: z.string().optional(),
  address: z.string().optional(),
  occupation: z.string().optional(),
  studentIds: z.array(z.string().uuid()).optional(),
});

export const createFeeSchema = z.object({
  name: z.string().min(1, "Fee name is required").max(100),
  type: z.enum([
    "TUITION",
    "REGISTRATION",
    "EXAM",
    "LIBRARY",
    "LABORATORY",
    "SPORTS",
    "TRANSPORT",
    "OTHER",
  ]),
  amount: z.number().positive("Amount must be positive"),
  currency: z.string().default("XAF"),
  dueDate: z.string().optional(),
  description: z.string().optional(),
  frequency: z.enum(["ONCE", "TERMLY", "YEARLY", "MONTHLY"]).default("ONCE"),
  applicableTo: z.enum(["ALL", "SPECIFIC_GRADE", "SPECIFIC_STUDENT"]).default("ALL"),
  grade: z.string().optional(),
});

export const createPaymentSchema = z.object({
  studentId: z.string().uuid("Student is required"),
  feeId: z.string().uuid("Fee is required"),
  amount: z.number().positive("Amount must be positive"),
  method: z.enum(["CASH", "CARD", "BANK_TRANSFER", "MOBILE_MONEY", "CHECK"]),
  reference: z.string().optional(),
  notes: z.string().optional(),
  paymentDate: z.string().optional(),
});

export const profileSchema = z.object({
  firstName: z.string().min(1, "First name is required").max(50),
  lastName: z.string().min(1, "Last name is required").max(50),
  phone: z.string().optional(),
});

export const changePasswordSchema = z
  .object({
    currentPassword: z.string().min(1, "Current password is required"),
    newPassword: z
      .string()
      .min(8, "Password must be at least 8 characters")
      .regex(/[A-Z]/, "Must contain an uppercase letter")
      .regex(/[0-9]/, "Must contain a number"),
    confirmPassword: z.string().min(1, "Please confirm your password"),
  })
  .refine((data) => data.newPassword === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });

export const institutionSettingsSchema = z.object({
  name: z.string().min(1, "Institution name is required").max(200),
  type: z.enum(["PRIVATE_SCHOOL", "PUBLIC_SCHOOL", "UNIVERSITY", "TRAINING_CENTER"]),
  address: z.string().optional(),
  phone: z.string().optional(),
  email: z.string().email("Invalid email").optional().or(z.literal("")),
  website: z.string().url("Invalid URL").optional().or(z.literal("")),
  logo: z.string().optional(),
  currency: z.string().default("XAF"),
  academicYear: z.string().optional(),
});


export type ProfileFormData = z.infer<typeof profileSchema>;
export type InstitutionSettingsFormData = z.infer<typeof institutionSettingsSchema>;
