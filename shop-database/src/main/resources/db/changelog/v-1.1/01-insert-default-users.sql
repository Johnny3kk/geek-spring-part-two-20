INSERT INTO `users` (`name`, `password`)
VALUE   ('admin', '$2y$12$R0sxrjCYiD2syiOClXtgReaYOmQoSHMeNgZL5ruDoQqs0uHMvIfU6'),
        ('manager', '$2y$12$R0sxrjCYiD2syiOClXtgReaYOmQoSHMeNgZL5ruDoQqs0uHMvIfU6'),
        ('guest', '$2y$12$R0sxrjCYiD2syiOClXtgReaYOmQoSHMeNgZL5ruDoQqs0uHMvIfU6');
GO

INSERT INTO `roles` (`name`)
VALUE ('ROLE_ADMIN'), ('ROLE_MANAGER'), ('ROLE_GUEST');
GO

INSERT INTO `users_roles`(`user_id`, `role_id`)
VALUE  ((SELECT id FROM `users` WHERE `name` = 'admin'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_ADMIN')),
        ((SELECT id FROM `users` WHERE `name` = 'manager'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_MANAGER')),
        ((SELECT id FROM `users` WHERE `name` = 'guest'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_GUEST'));
GO
