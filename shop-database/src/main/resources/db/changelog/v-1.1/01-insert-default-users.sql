INSERT INTO `users` (`name`, `password`)
VALUE   ('admin', '$2y$12$R0sxrjCYiD2syiOClXtgReaYOmQoSHMeNgZL5ruDoQqs0uHMvIfU6'),
        ('guest', '$2y$12$R0sxrjCYiD2syiOClXtgReaYOmQoSHMeNgZL5ruDoQqs0uHMvIfU6');
GO

INSERT INTO `roles` (`name`)
VALUE ('ROLE_ADMIN'), ('ROLE_GUEST');
GO

INSERT INTO `users_roles`(`user_id`, `role_id`)
SELECT (SELECT id FROM `users` WHERE `name` = 'admin'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_ADMIN')
UNION ALL
SELECT (SELECT id FROM `users` WHERE `name` = 'guest'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_GUEST');
GO
