interface PersonalData {
  id: number | null;
  firstname: string;
  lastname: string;
  email: string;
  phoneNumber: string;
  address: string;
  birthdate: string;
  thumbnail: string[] | null;
}

export default PersonalData;
