INSERT INTO user (user_name) VALUES ('공은서'), ('문재욱'), ('심규림'), ('곽현기'), ('김인후'), ('천동민'), ('고정범'),
                                    ('오원택'), ('이용만'), ('김동현'), ('신예진'), ('변지혜'), ('박정민'), ('김주형'),
                                    ('이철진'), ('김용철'), ('이정조'), ('김준성'), ('고지명'),
                                    ('주건정'), ('신은정'), ('윤성찬'), ('신제원'), ('우효진'),
                                    ('신국현'), ('재현이'), ('정연재'), ('김지혜'), ('홍세영'), ('허대영');

INSERT INTO lecture (lecture_name, lecturer) VALUES
    ('로이코치님과 함께하는 백엔드 강의', '로이'), ('렌코치님과 함께하는 백엔드 강의', '렌'),
    ('탸일러코치님과 함께하는 백엔드 강의', '탸일러'), ('토투코치님과 함께하는 백엔드 강의', '토투'),
    ('김종협코치님과 함께하는 백엔드 강의', '김종협'), ('허재코치님과 함께하는 백엔드 강의', '허재'),
    ('하현우코치님과 함께하는 백엔드 강의', '렌'), ('이석범코치님과 함께하는 백엔드 강의', '이석범');


INSERT INTO lecture_item (available_date, lecture_id, available_cnt) VALUES
     ('2024-05-11', 1, 30),
     ('2024-10-06', 2, 30),
     ('2024-10-08', 3, 0),
     ('2024-10-10', 4, 30),
     ('2024-10-12', 5, 30),
     ('2024-11-13', 6, 30),
     ('2024-11-05', 7, 30),
     ('2024-11-10', 8, 30);