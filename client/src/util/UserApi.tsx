import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
const jwtToken = localStorage.getItem('Authorization');
const refreshToken = localStorage.getItem('Refresh');
const url = '';

interface FetchHook {
  responseData: object | null;
  loading: boolean;
  error: string | null;
}

export const getUserInfo = (id: string): FetchHook => {
  const [responseData, setResponseData] = useState<object | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const headers = {
      'Content-Type': 'multipart/form-data',
      Authorization: jwtToken,
      Refresh: refreshToken,
    };
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await axios.get(`${url}/pets/${id}`, { headers });
        setResponseData(response.data);
      } catch (error) {
        setError(error as never);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [url]);

  return { responseData, loading, error };
};

export const petUpdate = async (
  petId: string,
  petname: string,
  age: number,
  gender: string,
  species: string,
  code: number,
  formData: { profileImage: string | Blob },
  navigate: any,
) => {
  if (!formData.profileImage) return;
  const headers = {
    'Content-Type': 'multipart/form-data',
    Authorization: jwtToken,
    Refresh: refreshToken,
  };
  const data = new FormData();
  data.append('petName', petname);
  data.append('age', age.toString());
  data.append('gender', gender);
  data.append('species', species);
  data.append('code', code.toString());
  data.append('profileImage', formData.profileImage);
  console.log('data', data);
  console.log('5', formData);
  console.log('6', formData.profileImage);
  console.log('7');
  for (const key of data.keys()) {
    console.log(key);
  }
  for (const value of data.values()) {
    console.log(value);
  }
  try {
    await axios.post(`${url}/patch/${petId}`, data, { headers });
    navigate('/Mypage');
  } catch (error) {
    console.error('Error', error);
    alert(error);
  }
};

export const petLogout = async () => {
  const headers = {
    Authorization: jwtToken,
  };
  try {
    await axios.post(
      `${url}/logout`,
      {
        accessToken: jwtToken,
        refreshToken: refreshToken,
      },
      { headers },
    );
  } catch (error) {
    console.error('Error', error);
    console.log(jwtToken);
  } finally {
    localStorage.removeItem('Authorization');
    localStorage.removeItem('Refresh');
    localStorage.removeItem('petId');
  }
};

export const petDelete = async (id: string) => {
  const headers = {
    Authorization: jwtToken,
    Refresh: refreshToken,
  };
  try {
    await axios.delete(`${url}/pets/${id}`, { headers });
  } catch (error) {
    console.error('Error', error);
  } finally {
    localStorage.removeItem('Authorization');
    localStorage.removeItem('Refresh');
  }
};
