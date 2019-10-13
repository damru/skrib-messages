INSERT INTO
    messages (author_id, rayon, latitude, longitude, body, created_date)
VALUES
    (0, 1000, 48.8534, 2.3488, 'Good morning Paris', SELECT now()),
    (0, 1000, 48.8167, 2.3833, 'Good morning Ivry', SELECT now());
