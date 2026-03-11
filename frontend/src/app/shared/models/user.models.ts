export interface User {
  id: number;
  fullName: string;
  email: string;
  cep?: string | null;
  street?: string | null;
  number?: string | null;
  complement?: string | null;
  neighborhood?: string | null;
  city?: string | null;
  state?: string | null;
  additionalInfo?: string | null;
  notes?: string | null;
}