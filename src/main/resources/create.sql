CREATE TABLE IF NOT EXISTS accounts (
  id VARCHAR(255) NOT NULL PRIMARY KEY,
  --DECIMAL(19, 4) is a popular choice for money (a quick Google bears this out)
  --DECIMAL(24, 8) sounds like overkill
  amount DECIMAL(19,4) NOT NULL
);