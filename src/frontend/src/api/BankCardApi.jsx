const BASE_URL = 'http://localhost:8080/api/cards';

export const addBankCard = async (owner, number, expirationDate, cvc, userId, navigate) => {
    try {
      const response = await fetch(`${BASE_URL}/create?userId=${userId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          owner,
          number,
          expirationDate,
          cvc,
        }),
      });
      if (!response.ok) {
        throw new Error('Failed to add bank card');
      }
      console.log('Bank card added successfully');
      navigate('/profile');
    } catch (error) {
      console.error('Error adding bank card:', error);
    }
  };