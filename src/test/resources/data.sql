INSERT INTO
    messages (author_id, rayon, latitude, longitude, body, created_date)
VALUES
    (1, 1000, 48.8534, 2.3488, 'New year in Paris', '2019-01-01T15:32:35.490026+02:00'),
    (1, 1000, 48.8167, 2.3833, 'New year in Ivry', '2019-01-01T15:32:35.490026+02:00'),
    (1, 1000, 48.8534, 2.3488, 'Good morning Paris', SELECT now()),
    (1, 1000, 48.8167, 2.3833, 'Good morning Ivry', SELECT now()),
    (1, 1000, 47.2088163, -1.5948576, 'Il pleut a Naoned', SELECT now());
