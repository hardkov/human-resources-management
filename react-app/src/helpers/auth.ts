import { getToken } from './storage';

const getAuthHeaders = () => {
  const token: string | null = getToken();

  return { Authorization: `Bearer ${token}` };
};

export { getAuthHeaders };
